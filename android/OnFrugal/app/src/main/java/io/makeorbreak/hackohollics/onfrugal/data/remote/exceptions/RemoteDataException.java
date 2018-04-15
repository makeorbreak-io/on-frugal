package io.makeorbreak.hackohollics.onfrugal.data.remote.exceptions;

public abstract class RemoteDataException extends Exception {
    private String  code;

    RemoteDataException(String code, String errorMessage) {
        super(errorMessage);
        this.code = code;
    }

    RemoteDataException(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getErrorMessage(){
        return super.getMessage();
    }
}
