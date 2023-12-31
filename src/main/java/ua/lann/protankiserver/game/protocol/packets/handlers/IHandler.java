package ua.lann.protankiserver.game.protocol.packets.handlers;

import io.netty.buffer.ByteBuf;
import ua.lann.protankiserver.ClientController;

public interface IHandler {
    void handle(ClientController channel, ByteBuf buf);
}
