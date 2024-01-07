package net.hassaku63.hellolucene;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.nio.file.Files;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.SegmentInfo;
import org.apache.lucene.index.StoredFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermVectors;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.Fields;
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

        /**
         * https://lucene.apache.org/core/9_1_0/core/org/apache/lucene/index/package-summary.html#stats
         * 
         */

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
             * ref: https://lucene.apache.org/core/9_1_0/core/org/apache/lucene/index/IndexReader.html
             */
            dir = FSDirectory.open(inputDirPath);
        } catch (Exception e) {
            System.out.println("Failed to open lucene index.");
            System.exit(1);
        }

        // create DirectoryReader
        // DirectoryReader は IndexReader の抽象サブクラス
        // 内部的には StandardDirectoryReader を使う
        // 
        // https://lucene.apache.org/core/9_1_0/core/org/apache/lucene/index/CompositeReader.html
        // > Instances of this reader type can only be used to get stored fields from the underlying LeafReaders, but it is not possible to directly retrieve postings. To do that, get the LeafReaderContext for all sub-readers via IndexReader.leaves().
        DirectoryReader dirReader = null;
        try {
            dirReader = DirectoryReader.open(dir);
        } catch (Exception e) {
            System.out.println("Failed to open lucene index.");
            System.exit(1);
        }

        // describe metadata
        // https://lucene.apache.org/core/9_1_0/core/org/apache/lucene/index/package-summary.html#stats
        System.out.println("# Directory metadata (Segment statistics)");
        System.out.println("Index directory path: " + indexDirPath);
        System.out.println("Index version: " + dirReader.getVersion());
        System.out.println("Number of documents (excluding deleted documents): " + dirReader.numDocs());
        System.out.println("Number of deleted documents: " + dirReader.numDeletedDocs());
        System.out.println("Number of leaves (idnexed fileds): " + dirReader.leaves().size());

        StoredFields fields = null;
        try {
            fields = dirReader.storedFields();
        } catch (Exception e) {
            System.out.println("Failed to get stored fields.");
            System.exit(1);
        }

        System.out.println();
        for (int i=0; i<dirReader.leaves().size(); i++) {
            System.out.println("## Leaf " + i + " metadata");

            Document doc = null;
            try {
                doc =  fields.document(i);
            } catch (Exception e) {
                System.out.println("Failed to get stored fields.");
                System.exit(1);
            }

            for (IndexableField f: doc.getFields()) {
                String content = f.stringValue();
                if (
                    f.name().equals("content")
                    && content.indexOf("\n") > 0
                ) {
                    content = content.substring(0, content.indexOf("\n")) + " ...";
                } 
                System.out.println("Field " + i + ": Name=" + f.name() + " Value=" + content);
            }

            LeafReaderContext leafReaderContext = dirReader.leaves().get(i);
            System.out.println("Leaf " + i + " number of documents: " + leafReaderContext.reader().numDocs());
            System.out.println("Leaf " + i + " number of deleted documents: " + leafReaderContext.reader().numDeletedDocs());

            LeafReader leafReader = leafReaderContext.reader();
            for (FieldInfo fi: leafReader.getFieldInfos()) {
                System.out.println("FieldInfo " + fi.getFieldNumber() + ": " + fi.getName());
                fi.attributes().forEach((k, v) -> {
                    System.out.println("\tKey=" + k + " Value=" + v);
                });
            }
            System.out.println();
        }

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