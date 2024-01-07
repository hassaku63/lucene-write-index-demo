package net.hassaku63.hellolucene;

import picocli.CommandLine;
import picocli.CommandLine.Command;
// import picocli.CommandLine.RunAll;

@Command(
    name = "main",
    description = "Hello Lucene Sample Application",
    mixinStandardHelpOptions = true,
    version = "0.1.0",
    subcommands = {
        TextIndexWriter.class
        // DescribeIndexMetadata.class,
    }
)
public class Main {
    public static void main(String[] args) {
        // CommandLine command = new CommandLine(new Cli(new CommandHandler()));
        CommandLine cmd = new CommandLine(new Main());
        // need to implement Runnable interface if you specify RunAll
        // cmd.setExecutionStrategy(new RunAll());

        int exitCode = cmd.execute(args);
        System.exit(exitCode);
    }
}