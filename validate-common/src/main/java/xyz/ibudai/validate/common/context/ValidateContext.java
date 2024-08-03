package xyz.ibudai.validate.common.context;

import java.lang.reflect.Field;

public class ValidateContext {

    private Object data;

    private Field field;

    private int group;

    private boolean serialize;

    public ValidateContext() {
    }

    public ValidateContext(Object data, Field field, int group, boolean serialize) {
        this.data = data;
        this.field = field;
        this.group = group;
        this.serialize = serialize;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public boolean isSerialize() {
        return serialize;
    }

    public void setSerialize(boolean serialize) {
        this.serialize = serialize;
    }
}
