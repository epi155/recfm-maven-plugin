package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.ClassModel;
import io.github.epi155.recfm.api.FieldDefault;
import io.github.epi155.recfm.api.TraitModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MasterBook {
    private String packageName;
    private FieldDefault defaults = new FieldDefault();
    private List<TraitModel> interfaces = new ArrayList<>();
    private List<ClassModel> classes = new ArrayList<>();
}
