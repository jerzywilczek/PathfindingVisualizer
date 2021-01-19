package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map;

public class Field {
    private FieldType fieldType;
    private FieldState fieldState;

    public Field(FieldType fieldType, FieldState fieldState){
        this.fieldType = fieldType;
        this.fieldState = fieldState;
    }

    public Field(FieldType fieldType){
        this(fieldType, FieldState.UNPROCESSED);
    }

    public Field(){
        this(FieldType.EMPTY, FieldState.UNPROCESSED);
    }

    public void setFieldType(FieldType fieldType){
        this.fieldType = fieldType;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public FieldState getFieldState() {
        return fieldState;
    }

    public void setFieldState(FieldState fieldState) {
        this.fieldState = fieldState;
    }
}
