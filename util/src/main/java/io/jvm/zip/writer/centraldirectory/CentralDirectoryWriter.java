package io.jvm.zip.writer.centraldirectory;

import io.jvm.zip.reader.centraldirectory.CentralDirectoryReader;
import io.jvm.zip.structure.centraldirectory.CentralDirectoryEnd;
import io.jvm.zip.structure.centraldirectory.CentralDirectoryRecord;
import io.jvm.zip.structure.localfile.CompressedData;
import io.jvm.zip.writer.localfile.LocalFileRecordWriter;

import java.util.Arrays;

public class CentralDirectoryWriter {

  public final CentralDirectoryRecord[] records;
  public final CentralDirectoryEnd endRecord;

  public CentralDirectoryWriter(final CentralDirectoryReader r) {
    records = r.records;
    endRecord = r.endRecord;
  }

  public void UpdateCentralDirectory(final LocalFileRecordWriter[] rec) {
    System.out.println("Update central directory records :");
    // update central directory records
    int offsetSum = 0;
    for (int index = 0; index < rec.length; index ++) {
      LocalFileRecordWriter r = rec[index];
      System.out.println("OLD LocalFileRecord Offset :" + records[index].getLocalFileHeaderOffset());
      System.out.println("NEW LocalFileRecord Offset :" + offsetSum);

      records[index].setLocalFileHeaderOffset(offsetSum);

      CompressedData newCD = r.localRecord.getCompressedDataObject();

      records[index].setCompressedSize(newCD.getLength());
      records[index].setCompressionMethod(newCD.Method);
      records[index].setCRC32(newCD.CRC32);

      offsetSum += rec[index].localRecord.getLength();

      // update start of central directory
      if (index == rec.length - 1) {
        System.out.println("OLD CentralDirectoryStart Offset :" + endRecord.getCentralDirectoryStartOffset());
        System.out.println("NEW CentralDirectoryStart Offset :" + offsetSum);
        endRecord.setCentralDirectoryStartOffset(offsetSum);
      }
    }
  }

  public byte[] getBytes() {
    return Arrays.copyOfRange(endRecord.body, records[0].offset, endRecord.body.length);
  }
}
