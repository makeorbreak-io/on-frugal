package io.makeorbreak.hackohollics.onfrugal.data.remote.exceptions;


public class BasicRemoteException extends RemoteDataException {
    public BasicRemoteException(String code, String errorMessage) {
        super(code, errorMessage);
    }

    public BasicRemoteException(String code) {
        super(code);
    }
}
