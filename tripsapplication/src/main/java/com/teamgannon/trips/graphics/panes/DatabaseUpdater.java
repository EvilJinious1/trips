package com.teamgannon.trips.graphics.panes;

import com.teamgannon.trips.graphics.entities.StarDisplayRecord;
import com.teamgannon.trips.jpa.model.AstrographicObject;

import java.util.UUID;

public interface DatabaseUpdater {

    void astrographicUpdate(UUID recordId, String notes);

    void astrographicUpdate(AstrographicObject record);

    AstrographicObject getStar(UUID starId);


    void removeStar(UUID fromString);
}
