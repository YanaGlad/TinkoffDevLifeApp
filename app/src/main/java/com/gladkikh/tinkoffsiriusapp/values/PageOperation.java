package com.gladkikh.tinkoffsiriusapp.values;

public enum PageOperation {
    STAND(0),
    NEXT(1),
    PREVIOUS(-1);

    private int pos;
    PageOperation(int pos){
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }
}
