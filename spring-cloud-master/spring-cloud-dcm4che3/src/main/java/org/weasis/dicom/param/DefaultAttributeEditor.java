package org.weasis.dicom.param;

import java.util.HashMap;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.util.UIDUtils;

public class DefaultAttributeEditor implements AttributeEditor {
    private HashMap<String, String> uidMap;
    private final boolean generateUIDs;
    private final Attributes tagToOverride;

    public DefaultAttributeEditor(Attributes tagToOverride) {
        this(false, tagToOverride);
    }

    /**
     * @param generateUIDs
     *            generate new UIDS for Study, Series and Instance
     * @param tagToOverride
     *            list of DICOM attributes to override
     * 
     */
    public DefaultAttributeEditor(boolean generateUIDs, Attributes tagToOverride) {
        this.generateUIDs = generateUIDs;
        this.tagToOverride = tagToOverride;
        this.uidMap = generateUIDs ? new HashMap<>() : null;
    }

    @Override
    public boolean apply(Attributes data, AttributeEditorContext context) {
        if (data != null) {
            boolean update = false;
            if (generateUIDs) {
                if ("2.25".equals(UIDUtils.getRoot())) {
                    UIDUtils.setRoot("2.25.35");
                }
                // New Study UID
                String oldStudyUID = data.getString(Tag.StudyInstanceUID);
                String studyUID = uidMap.computeIfAbsent(oldStudyUID, k -> UIDUtils.createUID());
                data.setString(Tag.StudyInstanceUID, VR.UI, studyUID);

                // New Series UID
                String oldSeriesUID = data.getString(Tag.SeriesInstanceUID);
                String seriesUID = uidMap.computeIfAbsent(oldSeriesUID, k -> UIDUtils.createUID());
                data.setString(Tag.SeriesInstanceUID, VR.UI, seriesUID);

                // New Sop UID
                String iuid = UIDUtils.createUID();
                data.setString(Tag.SOPInstanceUID, VR.UI, iuid);
                update = true;
            }
            if (tagToOverride != null && !tagToOverride.isEmpty()) {
                data.update(Attributes.UpdatePolicy.OVERWRITE, tagToOverride, null);
                update = true;
            }
            return update;
        }
        return false;
    }

}
