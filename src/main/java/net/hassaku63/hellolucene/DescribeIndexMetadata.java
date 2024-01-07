package net.hassaku63.hellolucene;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

@Command(
    name = "read",
    description = "Lucene Sample read application"
)
public class DescribeIndexMetadata implements Runnable {
    @Option(
        names = {"-i", "--index-dir"},
        description = "Lucene index directory path"
    )
    String indexDirPath;

    @Override
    public void run() {
        // check index directory exists
        Path inputDirPath = Paths.get(indexDirPath);
        if (!Files.isDirectory(inputDirPath)) {
            System.out.println("Index directory path not readable.");
            System.exit(1);
        }

        // create Directory
        FSDirectory dir = null;
        try {
            /**
             * 参考)
             * Writer と協調して動くなら、 DirectoryReader.open(IndexWriter) で IndexReader を取得するのがベスト、とある
             * 今回は CLI として Read/Write がそれぞれ単独で実行される想定なので使わない
             * 
             * It's best to use DirectoryReader.open(IndexWriter) to obtain an IndexReader, if your IndexWriter is in-process. 
             * When you need to re-open to see changes to the index, it's best to use DirectoryReader.openIfChanged(DirectoryReader) since the new reader will share resources with the previous one when possible.
             * 
             * ref: https://lucene.apache.org/core/7_2_1/core/org/apache/lucene/index/IndexReader.html
             */
            dir = FSDirectory.open(inputDirPath);
        } catch (Exception e) {
            System.out.println("Failed to open lucene index.");
            System.exit(1);
        }

        // create DirectoryReader
        DirectoryReader dirReader = null;
        try {
            dirReader = DirectoryReader.open(dir);
        } catch (Exception e) {
            System.out.println("Failed to open lucene index.");
            System.exit(1);
        }

        // describe metadata
        System.out.println("Index directory path: " + indexDirPath);
        System.out.println("Index version: " + dirReader.getVersion());
        System.out.println("Number of documents: " + dirReader.numDocs());
        System.out.println("Number of deleted documents: " + dirReader.numDeletedDocs());
        System.out.println("Number of segments: " + dirReader.leaves().size());

        // close
        try {
            dirReader.close();
        } catch (Exception e) {
            System.out.println("Failed to close lucene index.");
            System.exit(1);
        }

        try {
            dir.close();
        } catch (Exception e) {
            System.out.println("Failed to close lucene index.");
            System.exit(1);
        }
    }
}