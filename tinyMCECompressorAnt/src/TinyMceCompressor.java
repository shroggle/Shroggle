
import java.io.*;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class TinyMceCompressor extends Task {


    public void execute() throws BuildException {
        try {
            String suffix = "";
            String content = "";
            String plugins[] = this.plugins.replaceAll("[^0-9a-zA-Z\\-_,]+", "").split(",");
            String languages[] = this.languages.replaceAll("[^0-9a-zA-Z\\-_,]+", "").split(",");
            String themes[] = this.themes.replaceAll("[^0-9a-zA-Z\\-_,]+", "").split(",");

            String absPath = getAbsPath();
            String resourcesPath = absPath + "\\resources\\tiny_mce";
            String explodedPath = absPath + "\\exploded\\tiny_mce";

            // Add TinyMCE_GZ
            content += getTinyMceGzValue();
            // Add core
            content += getFileContents(resourcesPath + "\\" + ("old_tiny_mce" + suffix + ".js"));

            // Patch loading functions
            content += "tinyMCE_GZ.start();";

            // Add core languages
            for (String language : languages) {
                content += getFileContents(resourcesPath + "\\" + ("langs/" + language + ".js"));
            }
            // Add themes
            for (String theme : themes) {
                content += getFileContents(resourcesPath + "\\" + ("themes/" + theme + "/editor_template" + suffix + ".js"));
                for (String language : languages) {
                    content += getFileContents(resourcesPath + "\\" + ("themes/" + theme + "/langs/" + language + ".js"));
                }
            }
            // Add plugins
            for (String plugin : plugins) {
                content += getFileContents(resourcesPath + "\\" + ("plugins/" + plugin + "/editor_plugin" + suffix + ".js"));
                for (String language : languages) {
                    content += getFileContents(resourcesPath + "\\" + ("plugins/" + plugin + "/langs/" + language + ".js"));
                }
            }
            // Restore loading functions
            content += "tinyMCE_GZ.end(); ";
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            OutputStreamWriter bow = new OutputStreamWriter(bos);
            bow.write(content);
            bow.close();
            // Write to file
            FileOutputStream fout;

            fout = new FileOutputStream(resourcesPath + "\\tiny_mce.js");
            fout.write(bos.toByteArray());
            fout.close();
            fout = new FileOutputStream(explodedPath + "\\tiny_mce.js");
            fout.write(bos.toByteArray());
            fout.close();
        } catch (Exception e) {
            throw new BuildException();
        }
    }

    private String getAbsPath() {
        File currentDirectory = new File("").getAbsoluteFile();
        File parentFile = currentDirectory;
        String absPath = "";
        while (parentFile != null) {
            parentFile = currentDirectory.getParentFile();
            currentDirectory = parentFile;
            if (parentFile != null) {
                for (String fileName : parentFile.list()) {
                    if (fileName.equals("resources")) {
                        absPath = parentFile.getAbsolutePath();
                        parentFile = null;
                        break;
                    }
                }
            }
        }
        return absPath;
    }

    private String getFileContents(String path) {
        try {
            if (!new File(path).exists()) {
                return "";
            }
            FileInputStream fis = new FileInputStream(path);
            int x = fis.available();
            byte b[] = new byte[x];
            fis.read(b);
            return new String(b);
        } catch (Exception e) {
            throw new BuildException();
        }
    }


    private String getTinyMceGzValue() {
        return "var tinyMCE_GZ = {\n" +
                "    settings : {\n" +
                "        themes : \"" + themes + "\",\n" +
                "        plugins : \"" + plugins + "\",\n" +
                "        languages : \"" + languages + "\",\n" +
                "        suffix : ''\n" +
                "    },\n" +
                "\n" +
                "    start : function() {\n" +
                "        var t = this, each = tinymce.each, s = t.settings, ln = s.languages.split(',');\n" +
                "\n" +
                "        tinymce.suffix = s.suffix;\n" +
                "\n" +
                "        function load(u) {\n" +
                "            tinymce.ScriptLoader.markDone(tinyMCE.baseURI.toAbsolute(u));\n" +
                "        }\n" +
                "        ;\n" +
                "\n" +
                "        // Add core languages\n" +
                "        each(ln, function(c) {\n" +
                "            if (c)\n" +
                "                load('langs/' + c + '.js');\n" +
                "        });\n" +
                "\n" +
                "        // Add themes with languages\n" +
                "        each(s.themes.split(','), function(n) {\n" +
                "            if (n) {\n" +
                "                load('themes/' + n + '/editor_template' + s.suffix + '.js');\n" +
                "\n" +
                "                each(ln, function(c) {\n" +
                "                    if (c)\n" +
                "                        load('themes/' + n + '/langs/' + c + '.js');\n" +
                "                });\n" +
                "            }\n" +
                "        });\n" +
                "\n" +
                "        // Add plugins with languages\n" +
                "        each(s.plugins.split(','), function(n) {\n" +
                "            if (n) {\n" +
                "                load('plugins/' + n + '/editor_plugin' + s.suffix + '.js');\n" +
                "\n" +
                "                each(ln, function(c) {\n" +
                "                    if (c)\n" +
                "                        load('plugins/' + n + '/langs/' + c + '.js');\n" +
                "                });\n" +
                "            }\n" +
                "        });\n" +
                "    },\n" +
                "\n" +
                "    end : function() {\n" +
                "    }\n" +
                "};";
    }

    public void setPlugins(String plugins) {
        this.plugins = plugins;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public void setThemes(String themes) {
        this.themes = themes;
    }

    private String plugins;// = "example,safari,layer,table,save,advhr,advimage,advlink,emotions,iespell,preview,media,searchreplace,contextmenu,paste,directionality,template,inlinepopups";
    private String languages;// = "en";
    private String themes;// = "advanced";
}
