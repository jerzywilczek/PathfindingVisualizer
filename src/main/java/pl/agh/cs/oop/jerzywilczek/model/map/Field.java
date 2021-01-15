package pl.agh.cs.oop.jerzywilczek.model.map;

public class Field {
    private FieldType fieldType;
    private boolean visited = false;

    public Field(FieldType fieldType){
        this.fieldType = fieldType;
    }

    public Field(){
        this(FieldType.EMPTY);
    }
}
