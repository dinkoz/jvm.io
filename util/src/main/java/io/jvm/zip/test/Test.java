package io.jvm.zip.test;

import io.jvm.zip.reader.ZipReader;
import io.jvm.zip.structure.centraldirectory.CentralDirectoryRecord;
import io.jvm.zip.structure.localfile.CompressedData;
import io.jvm.zip.writer.ZipWriter;
import io.jvm.zip.writer.localfile.LocalFileRecordWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class Test {

  /**
   * @param args
   */
  public static void main(String[] args) {

    RandomAccessFile f;
    try {
      f = new RandomAccessFile("/home/dinko/workspace-code/test.jar", "r");
      byte[] body = new byte[(int)f.length()];
      f.read(body);
      f.close();

      ZipReader zip = new ZipReader(body);

      System.out.println("CENTRAL DIRECTORY RECORDS [" + zip.centralDirectory.records.length + "] : \n");
      for (CentralDirectoryRecord r : zip.centralDirectory.records) {
        System.out.println(r.toString());
      }

//      System.out.println("\nLOCAL FILE RECORDS [" + zip.localFileRecords.length + "] : \n");
//      for (LocalFileRecordReader r : zip.localFileRecords) {
//        System.out.println(r.toString());
//      }

      ZipWriter ze = new ZipWriter(zip);

      for (int i = 0; i < ze.records.length; i++) {
        CompressedData oldCD = zip.localFileRecords[i].localFileRecord.getCompressedDataObject();
        ze.records[i] = new LocalFileRecordWriter(zip.localFileRecords[i], oldCD);
      }

      ze.UpdateCentralDirectory();

      ze.writeToFile("/home/dinko/workspace-code/testOutput.zip");

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
