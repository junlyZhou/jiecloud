package org.weasis.dicom.mf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.weasis.core.api.util.LangUtil;

public abstract class AbstractQueryResult implements QueryResult {

    protected final Map<String, Patient> patientMap;
    protected ViewerMessage viewerMessage;

    public AbstractQueryResult() {
        this(null);
    }

    public AbstractQueryResult(List<Patient> patients) {
        this.patientMap = new HashMap<>();
        for (Patient p : LangUtil.emptyIfNull(patients)) {
            addPatient(p);
        }
    }

    @Override
    public void removePatientId(List<String> patientIdList, boolean containsIssuer) {
        if (patientIdList != null && !patientIdList.isEmpty()) {
            if (containsIssuer) {
                patientIdList.forEach(patientMap::remove);
            } else {
                List<String> list = new ArrayList<>();
                for (String patientID : patientIdList) {
                    synchronized (patientMap) {
                        for (Patient p : patientMap.values()) {
                            if (p.getPatientID().equals(patientID)) {
                                list.add(p.getPseudoPatientUID());
                                break;
                            }
                        }
                    }
                }
                list.forEach(patientMap::remove);
            }
        }
    }

    @Override
    public void removeStudyUid(List<String> studyUidList) {
        if (studyUidList != null && !studyUidList.isEmpty()) {
            synchronized (patientMap) {
                for (Patient p : patientMap.values()) {
                    for (String studyUID : studyUidList) {
                        p.removeStudy(studyUID);
                    }
                }
            }
            removeItemsWithoutElements();
        }
    }

    @Override
    public void removeAccessionNumber(List<String> accessionNumberList) {
        if (accessionNumberList != null && !accessionNumberList.isEmpty()) {
            synchronized (patientMap) {
                for (Patient p : patientMap.values()) {
                    Iterator<Entry<String, Study>> studyIt = p.getEntrySet().iterator();
                    while (studyIt.hasNext()) {
                        Study study = studyIt.next().getValue();
                        if (!accessionNumberList.contains(study.getAccessionNumber())) {
                            studyIt.remove();
                        }
                    }
                }
            }
            removeItemsWithoutElements();
        }
    }

    @Override
    public void removeSeriesUid(List<String> seriesUidList) {
        if (seriesUidList != null && !seriesUidList.isEmpty()) {
            synchronized (patientMap) {
                for (Patient p : patientMap.values()) {
                    for (Study study : p.getStudies()) {
                        for (String seriesUID : seriesUidList) {
                            study.removeSeries(seriesUID);
                        }
                    }
                }
            }
            removeItemsWithoutElements();
        }
    }

    @Override
    public void removeItemsWithoutElements() {
        synchronized (patientMap) {
            Iterator<Entry<String, Patient>> patientIt = patientMap.entrySet().iterator();
            while (patientIt.hasNext()) {
                Patient p = patientIt.next().getValue();

                Iterator<Entry<String, Study>> studyIt = p.getEntrySet().iterator();
                while (studyIt.hasNext()) {
                    Study study = studyIt.next().getValue();

                    study.getEntrySet().removeIf(s -> s.getValue().isEmpty());
                    if (study.isEmpty()) {
                        studyIt.remove();
                    }
                }
                if (p.isEmpty()) {
                    patientIt.remove();
                }
            }
        }
    }

    public void addPatient(Patient p) {
        if (p != null) {
            patientMap.put(p.getPseudoPatientUID(), p);
        }
    }

    public Patient removePatient(String patientID, String issuerOfPatientID) {
        if (patientID == null) {
            return null;
        }
        StringBuilder key = new StringBuilder(patientID);
        if (issuerOfPatientID != null) {
            key.append(issuerOfPatientID);
        }
        return patientMap.remove(key.toString());
    }

    public Patient getPatient(String patientID, String issuerOfPatientID) {
        if (patientID == null) {
            return null;
        }
        StringBuilder key = new StringBuilder(patientID);
        if (issuerOfPatientID != null) {
            key.append(issuerOfPatientID);
        }
        return patientMap.get(key.toString());
    }

    @Override
    public Map<String, Patient> getPatients() {
        return patientMap;
    }

    @Override
    public ViewerMessage getViewerMessage() {
        return viewerMessage;
    }

    @Override
    public void setViewerMessage(ViewerMessage viewerMessage) {
        this.viewerMessage = viewerMessage;
    }

}