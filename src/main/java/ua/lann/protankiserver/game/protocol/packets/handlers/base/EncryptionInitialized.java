package ua.lann.protankiserver.game.protocol.packets.handlers.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;
import ua.lann.protankiserver.game.localization.Locale;
import ua.lann.protankiserver.game.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.game.protocol.packets.handlers.IHandler;
import ua.lann.protankiserver.game.screens.auth.AuthorizationScreen;

@PacketHandler(packetId = PacketId.CryptoInitialized)
public class EncryptionInitialized implements IHandler {
    private final ICodec<String> stringCodec = CodecRegistry.getCodec(String.class);

    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        channel.sendPacket(PacketId.Ping, Unpooled.buffer());
        channel.setLocale(Locale.fromString(stringCodec.decode(buf)));

        channel.getScreenManager().setScreen(AuthorizationScreen.class);
    }
}
