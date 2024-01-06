package net.hassaku63.hellolucene;

import java.util.List;
import picocli.CommandLine;
import picocli.CommandLine.ParseResult;

public class Main {
    public static void main(String[] args) {
        // CommandLine command = new CommandLine(new Cli(new CommandHandler()));
        CommandLine command = new CommandLine(new Cli());
        // ParseResult result = command.parseArgs(args);
        // List<String> expandedArgs = result.expandedArgs();
        // System.out.println("## args");
        // for (String arg : expandedArgs) {
        //     System.out.println(arg);
        // }
        // System.out.println("## args");

        // if (command.isUsageHelpRequested()) {
        //     command.usage(System.out);
        //     System.exit(0);
        // } else if (command.isVersionHelpRequested()) {
        //     command.printVersionHelp(System.out);
        //     System.exit(0);
        // }
        int exitCode = command.execute(args);
        System.exit(exitCode);
    }
}