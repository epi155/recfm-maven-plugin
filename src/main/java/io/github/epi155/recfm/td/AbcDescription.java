package io.github.epi155.recfm.td;

import io.github.epi155.recfm.api.*;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

@Slf4j
public class AbcDescription extends TypeDescription {
    private final CodeFactory factory;

    public AbcDescription(CodeFactory factory) {
        super(AbcModel.class, "!Abc");
        this.factory = factory;
        substituteProperty("at", Integer.class, null, "setOffset");
        substituteProperty("len", int.class, null, "setLength");
        //substituteProperty("pad", Character.class, null, "setPadChar");
        substituteProperty("chk", CheckAbc.class, null, "setCheck");
        substituteProperty("ovr", boolean.class, null, "setOverride");
        substituteProperty("ovf", OverflowAction.class, null, "setOnOverflow");
        substituteProperty("unf", UnderflowAction.class, null, "setOnUnderflow");
        substituteProperty("nrm", NormalizeAbcMode.class, null, "setNormalize");
        substituteProperty("get", Boolean.class, null, "setCheckGetter");
        substituteProperty("set", Boolean.class, null, "setCheckSetter");
    }
    public Object newInstance(Node node) {
        log.info("node-c: {}", node.getClass().getName());
        return factory.newAbcModel();
    }

    public Object newInstance(String propertyName, Node node) {
        log.info("node-p {}: {}", propertyName, node.getClass().getName());
        return null;
    }

}
