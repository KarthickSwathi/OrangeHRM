package genericLib;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

/**
 * PageClassGenerator
 *
 * Reads CSV with columns:
 * Sl No,Page/Screen,Element Name,Element Type,Locator Name,Locator Value
 *
 * Produces one Java Page Object class (single file) where each element becomes a single method:
 *   public void <elementName><ElementType>() { ... }
 *
 * Merge logic:
 * - If an existing method with same name is present and the body is identical -> keep old
 * - If bodies differ -> override with new
 */
public class SmartPageClassGenerator {

    // ----------------- ELEMENT MODEL -----------------
    public static class ElementData {
        String slNo;
        String elementName;
        String elementType;
        List<Locator> locators = new ArrayList<>(); // preserve input order

        static class Locator {
            String name; // raw name like xpath, id, name, className, tagName, css
            String value;
            Locator(String n, String v) { name = n == null ? "" : n.trim(); value = v == null ? "" : v; }
        }
    }

    static String outputFolder;

    // ----------------- MAIN -----------------
    public static void main(String[] args) throws Exception {

        // ===== INPUTS =====
        String csvFile = "C:/Users/User/Desktop/PimPage.csv";
        String className = "PimPage";
        outputFolder = "./src/main/java/webPages/";
        // ===================

        // ensure output directory exists
        Path outDir = Paths.get(outputFolder);
        if (!Files.exists(outDir)) Files.createDirectories(outDir);

        List<ElementData> elements = parseCSV(csvFile);
        Map<String, String> newMethods = buildMethods(elements);

        Path classFile = Paths.get(outputFolder + className + ".java");
        boolean exists = Files.exists(classFile);

        Map<String, String> existingMethods = new LinkedHashMap<>();
        if (exists) {
            existingMethods = extractMethods(new String(Files.readAllBytes(classFile)));
        }

        // MERGE
        Map<String, String> finalMethods = new LinkedHashMap<>();

        for (String methodName : newMethods.keySet()) {
            String newBody = newMethods.get(methodName);

            if (existingMethods.containsKey(methodName)) {
                String oldBody = existingMethods.get(methodName);
                if (oldBody.equals(newBody)) {
                    System.out.println(methodName + " → SAME, keeping existing");
                    finalMethods.put(methodName, oldBody);
                } else {
                    System.out.println(methodName + " → UPDATED, overriding with new");
                    finalMethods.put(methodName, newBody);
                }
                existingMethods.remove(methodName);
            } else {
                System.out.println(methodName + " → NEW, adding");
                finalMethods.put(methodName, newBody);
            }
        }

        // keep leftover old methods (not present in CSV)
        for (String leftover : existingMethods.keySet()) {
            System.out.println(leftover + " → OLD, keeping");
            finalMethods.put(leftover, existingMethods.get(leftover));
        }

        String finalClass = assembleClass(className, finalMethods);
        Files.write(classFile, finalClass.getBytes());

        System.out.println("✔ Final merged class generated: " + classFile.toAbsolutePath());
    }

    // ----------------- PARSE CSV -----------------
    public static List<ElementData> parseCSV(String filePath) throws Exception {
        List<ElementData> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNum = 0;
            ElementData current = null;

            while ((line = br.readLine()) != null) {
                lineNum++;
                if (line.trim().isEmpty()) continue;

                // skip header if appears
                if (lineNum == 1) {
                    String lc = line.toLowerCase();
                    if (lc.contains("sl") || lc.contains("element") || lc.contains("locator")) {
                        continue;
                    }
                }

                List<String> cols = splitCsv(line);
                while (cols.size() < 6) cols.add("");

                String slNo = cols.get(0).trim();
               // String page = cols.get(1).trim(); // unused for now
                String elementName = cols.get(2).trim();
                String elementType = cols.get(3).trim();
                String locatorName = cols.get(4).trim();
                String locatorValue = cols.get(5).trim();

                if (!slNo.isEmpty()) {
                    // new element
                    current = new ElementData();
                    current.slNo = slNo;
                    current.elementName = elementName;
                    current.elementType = elementType;
                    if (!locatorName.isEmpty() || !locatorValue.isEmpty()) {
                        current.locators.add(new ElementData.Locator(locatorName, locatorValue));
                    }
                    result.add(current);
                } else {
                    // additional locator row for current element
                    if (current != null) {
                        if (!locatorName.isEmpty() || !locatorValue.isEmpty()) {
                            current.locators.add(new ElementData.Locator(locatorName, locatorValue));
                        }
                    } else {
                        // defensive: no current element -> skip
                        System.err.println("Warning: locator row without preceding element at line " + lineNum);
                    }
                }
            }
        }
        return result;
    }

    // simple CSV splitter that handles quoted fields and escaped quotes ("")
    private static List<String> splitCsv(String line) {
        List<String> out = new ArrayList<>();
        if (line == null) return out;
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    cur.append('"'); // escaped quote
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                out.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        out.add(cur.toString());
        // trim tokens
        for (int i = 0; i < out.size(); i++) {
            out.set(i, out.get(i) == null ? "" : out.get(i).trim());
        }
        return out;
    }

    // ----------------- BUILD LOCATOR -----------------
    // Normalize locator name and escape value for Java string literal
    public static String buildLocatorExpr(String name, String value) {
        String n = name == null ? "" : name.trim();
        String v = value == null ? "" : value;
        // escape backslash and double-quote
        v = v.replace("\\", "\\\\").replace("\"", "\\\"");
        String nl = n.toLowerCase();
        if (nl.equals("xpath")) return "By.xpath(\"" + v + "\")";
        if (nl.equals("id")) return "By.id(\"" + v + "\")";
        if (nl.equals("name")) return "By.name(\"" + v + "\")";
        if (nl.equals("classname") || nl.equals("className")) return "By.className(\"" + v + "\")";
        if (nl.equals("tagname") || nl.equals("tagName")) return "By.tagName(\"" + v + "\")";
        if (nl.equals("css") || nl.equals("cssselector") || nl.equals("cssSelector")) return "By.cssSelector(\"" + v + "\")";
        if (nl.equals("linktext")) return "By.linkText(\"" + v + "\")";
        if (nl.equals("partiallinktext")) return "By.partialLinkText(\"" + v + "\")";
        // fallback
        return "By.xpath(\"" + v + "\")";
    }

    // ----------------- BUILD METHODS FROM ELEMENTS -----------------
    // Method name: camelCase(elementName) + Capitalized(elementType) e.g. loginText, usernameTextfield
    public static Map<String, String> buildMethods(List<ElementData> list) {
        Map<String, String> map = new LinkedHashMap<>();

        for (ElementData e : list) {
            String elemName = e.elementName == null ? "element" : e.elementName;
            String elemType = e.elementType == null ? "" : e.elementType;
            String methodName = toMethodName(elemName) + capitalize(elemType);

            StringBuilder sb = new StringBuilder();
            sb.append("\tpublic ArrayList<Object> ").append(methodName).append("() {\n");
            sb.append("\t\tArrayList<Object> ele = new ArrayList<>();\n");
            sb.append("\t\tele.add(\"").append(escapeForJava(e.elementName)).append("\");\n");
            sb.append("\t\tele.add(\"").append(escapeForJava(e.elementType)).append("\");\n");

            for (ElementData.Locator loc : e.locators) {
                String byExpr = buildLocatorExpr(loc.name, loc.value);
                sb.append("\t\tele.add(").append(byExpr).append(");\n");
            }
            sb.append("\t\treturn ele;\n");
            sb.append("\t}\n\n");

            map.put(methodName, sb.toString());
        }

        return map;
    }

    private static String escapeForJava(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String toMethodName(String raw) {
        if (raw == null || raw.trim().isEmpty()) return "element";
        // remove illegal chars, split by spaces/non-alnum, camel-case
        String cleaned = raw.replaceAll("[^A-Za-z0-9 ]+", " ").trim();
        String[] parts = cleaned.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String p = parts[i];
            if (p.isEmpty()) continue;
            if (i == 0) sb.append(p.substring(0, 1).toLowerCase()).append(p.substring(1));
            else sb.append(p.substring(0,1).toUpperCase()).append(p.substring(1));
        }
        return sb.length() == 0 ? "element" : sb.toString();
    }

    private static String capitalize(String s) {
        if (s == null || s.trim().isEmpty()) return "";
        s = s.trim();
        return s.substring(0,1).toUpperCase() + (s.length() > 1 ? s.substring(1) : "");
    }

 // ----------------- EXTRACT EXISTING METHODS -----------------
    public static Map<String, String> extractMethods(String javaSrc) {
        Map<String, String> map = new LinkedHashMap<>();

        // FIXED: matches any return type (void, String, ArrayList<Object>, Map<X,Y>, etc.)
        String regex = "(public\\s+[A-Za-z0-9_<>,\\s]+\\s+(\\w+)\\s*\\([^)]*\\)\\s*\\{)";
        Pattern startP = Pattern.compile(regex);
        Matcher startM = startP.matcher(javaSrc);

        while (startM.find()) {
            int startIdx = startM.start(1);
            String methodName = startM.group(2);

            // Find the opening brace
            int bracePos = javaSrc.indexOf("{", startM.end(1) - 1);
            if (bracePos < 0) continue;

            int idx = bracePos;
            int depth = 0;
            boolean started = false;

            // Scan until matching closing brace
            for (; idx < javaSrc.length(); idx++) {
                char c = javaSrc.charAt(idx);
                if (c == '{') {
                    depth++;
                    started = true;
                } else if (c == '}') {
                    depth--;
                    if (started && depth == 0) {
                        String methodFull = javaSrc.substring(startIdx, idx + 1);
                        map.put(methodName, methodFull + "\n");
                        break;
                    }
                }
            }
        }

        return map;
    }

    // ----------------- BUILD FINAL CLASS -----------------
    public static String assembleClass(String className, Map<String, String> methods) {
        StringBuilder sb = new StringBuilder();
        String normalized = outputFolder.replace("\\", "/");
        String[] parts = normalized.split("/");

        String packageName = "webPages";
        for (int i = parts.length - 1; i >= 0; i--) {
            if (parts[i] != null && !parts[i].trim().isEmpty()) {
                packageName = parts[i].trim();
                break;
            }
        }
        packageName = packageName.replaceAll("[^A-Za-z0-9_\\.]", "");
        if (packageName.isEmpty()) packageName = "webPages";

        sb.append("package ").append(packageName).append(";\n\n");
        sb.append("import java.util.ArrayList;\n");
        sb.append("import org.openqa.selenium.By;\n\n");
        sb.append("public class ").append(className).append(" {\n\n");

        for (String body : methods.values()) {
            sb.append(body);
        }

        sb.append("}\n");
        return sb.toString();
    }
}
