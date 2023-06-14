package io.github.epi155.recfm.td;

import io.github.epi155.recfm.api.FieldDefault;
import io.github.epi155.recfm.api.LoadOverflowAction;
import io.github.epi155.recfm.api.LoadUnderflowAction;
import org.yaml.snakeyaml.TypeDescription;

public class ClsDfltDescription extends TypeDescription {
    public ClsDfltDescription() {
        super(FieldDefault.ClsDefault.class);
        substituteProperty("ovf", LoadOverflowAction.class, null, "setOnOverflow");
        substituteProperty("unf", LoadUnderflowAction.class, null, "setOnUnderflow");
    }
}
