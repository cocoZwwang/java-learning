package pers.cocoadel.learning.socket.echo.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface TCPProtocol {
    void handleAccept(SelectionKey selectionKey) throws IOException;

    void handleRead(SelectionKey selectionKey) throws IOException;

    void handleWrite(SelectionKey selectionKey) throws IOException;
}
