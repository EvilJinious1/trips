package com.teamgannon.trips.file.chview.model;

import com.teamgannon.trips.file.chview.ChViewRecord;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A file descriptor of an input file that was read
 * <p>
 * Created by larrymitchell on 2017-02-07.
 */
@Data
public class ChViewFile implements Serializable {

    /**
     * records in the files
     * <p>
     * fourth
     */
    private final Map<Integer, ChViewRecord> records = new HashMap<>();
    /**
     * read as a long (4 bytes)
     * <p>
     * first element
     */
    private int fileVersion;
    /**
     * the view preferences
     * <p>
     * second element
     */
    private CHViewPreferences CHViewPreferences;
    /**
     * number of records in the file
     * <p>
     * third
     */
    private int numberOfRecords;
    /**
     * the postamble of the file
     * comment
     * <p>
     * fifth
     */
    private String comments;

    /**
     * the original file name that was read
     */
    private String originalFileName;

    /**
     * add a record
     *
     * @param chViewRecord the record to add
     */
    public void addRecord(@NotNull ChViewRecord chViewRecord) {
        records.put(chViewRecord.getRecordNumber(), chViewRecord);
    }


    /**
     * get a record from the file
     *
     * @param recordNumber the record number
     * @return the record
     */
    public ChViewRecord getRecord(int recordNumber) {
        return records.get(recordNumber);
    }
}
