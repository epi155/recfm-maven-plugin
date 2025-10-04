package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.ClassModel;
import io.github.epi155.recfm.api.FieldDefault;
import io.github.epi155.recfm.api.TraitModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static io.github.epi155.recfm.exec.RecordFormatMojo.pluginContext;

@Data
public class MasterBook {
    private FieldDefault defaults = new FieldDefault();
    private List<ClassPackage> packages = new ArrayList<>();

    @Getter
    public static class ClassPackage {
        public void setName(String name) {
            this.name = name;
            pluginContext.get().setCurrentPackage(name);
        }

        private String name;
        @Setter
        private List<TraitModel> interfaces = new ArrayList<>();
        @Setter
        private List<ClassModel> classes = new ArrayList<>();
    }
}
