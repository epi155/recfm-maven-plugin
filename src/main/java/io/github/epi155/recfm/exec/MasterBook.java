package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.ClassModel;
import io.github.epi155.recfm.api.FieldDefault;
import io.github.epi155.recfm.api.TraitModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MasterBook {
    private FieldDefault defaults = new FieldDefault();
    private List<ClassPackage> packages = new ArrayList<>();

    @Data
    public static class ClassPackage {
        private String name;
        private List<TraitModel> interfaces = new ArrayList<>();
        private List<ClassModel> classes = new ArrayList<>();
    }
}
