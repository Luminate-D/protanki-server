package ua.lann.protankiserver.game.screens.auth;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.Server;
import ua.lann.protankiserver.ServerSettings;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.game.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.game.resources.ResourcesPack;
import ua.lann.protankiserver.game.screens.ScreenBase;
import ua.lann.protankiserver.game.screens.lobby.BattleSelectScreen;
import ua.lann.protankiserver.security.BCryptHasher;

public class AuthorizationScreen extends ScreenBase {
    private static final int LoginScreenBackground = 122842;
    private static final boolean EmailRequired = false;
    private static final int MaxLength = 100;
    private static final int MinLength = 5;

    private boolean socialButtonsInit = false;
    private boolean captchaPositionsInit = false;

    public AuthorizationScreen(ClientController controller) {
        super(controller);
    }

    private void initSocialButtons() {
        ByteBuf buffer = Unpooled.buffer();
        ICodec<String> stringCodec = CodecRegistry.getCodec(String.class);

        stringCodec.encode(buffer, "https://oauth.vk.com/authorize?client_id=7889475&response_type=code&display=page&redirect_uri=http://146.59.110.195:8090/externalEntrance/vkontakte/?session=-1753613718684519995");
        stringCodec.encode(buffer, "vkontakte");

        controller.sendPacket(PacketId.InitLoginSocialButtons, buffer);
        buffer.release();

        socialButtonsInit = true;
    }


    private void initCaptchaPositions() {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(0);

        controller.sendPacket(PacketId.InitCaptchaPositions, buffer);
        buffer.release();

        captchaPositionsInit = true;
    }

    private void openAuthScreen() {
        ByteBuf buffer = Unpooled.buffer();
        ICodec<Boolean> boolCodec = CodecRegistry.getCodec(Boolean.class);

        if(ServerSettings.InviteOnly) {
            boolCodec.encode(buffer, true);
            controller.sendPacket(PacketId.RequireInviteCode, buffer);
        } else {
            buffer.writeInt(LoginScreenBackground);
            boolCodec.encode(buffer, EmailRequired);
            buffer.writeInt(MaxLength);
            buffer.writeInt(MinLength);

            controller.sendPacket(PacketId.InitLoginPage, buffer);
        }

        controller.sendPacket(PacketId.RemoveLoading, buffer);
        buffer.release();
    }

    @Override
    public void close() {
        this.controller.sendPacket(PacketId.RemoveLoginForm, Unpooled.buffer());
    }

    public void invalidCredentials() {
        this.controller.sendPacket(PacketId.InvalidCredentials, Unpooled.buffer());
    }

    public void authorize(Player player, String password) {
        boolean isPasswordValid = BCryptHasher.verify(player.getPassword(), password);
        if(!isPasswordValid) {
            this.invalidCredentials();
            return;
        }

        close();

        controller.setPlayer(player);
        controller.getProfileManager().sendPremiumInfo();
        controller.getProfileManager().sendProfileInfo();
        controller.getProfileManager().sendEmailInfo();
        controller.getResourcesManager().loadSingle(115361);

        controller.getResourcesManager().load(new Integer[] {}, () -> {
            controller.getFriendsManager().loadFriendsList();
            controller.initAchievements();
            controller.getScreenManager().setScreen(BattleSelectScreen.class);

            Server.getInstance().addActiveController(controller);
        }, 1000);
    }

    @Override
    public void open() {
        if(!socialButtonsInit) initSocialButtons();
        if(!captchaPositionsInit) initCaptchaPositions();

        controller.getResourcesManager().load(ResourcesPack.Main, this::openAuthScreen);
    }
}
