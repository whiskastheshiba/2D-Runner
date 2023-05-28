package MenuObjects;

import Enviroment.*;
import Game.*;

import java.awt.*;
import java.sql.SQLException;

public class SetUserNameButton extends MenuObject{

    private boolean userNameValid;
    private Texture tex = Game.getInstance();
    public SetUserNameButton(int x, int y, MenuObjectID id, int width, int height) {
        super(x, y, id, width, height);
        userNameValid = true;
    }

    @Override
    public void onClick(Game game) throws SQLException {
        if(game.userName.length() > 3 && game.userName.length() < 25 && !mySqlDatabase.isUserOnline(game.userName)){
            mySqlDatabase.setUserOnline(game.userName);
            userNameValid = true;
            game.gameState = GameState.MENU;

            Game.logger.info("Username set: " + game.userName);
            game.userId = mySqlDatabase.getUserID(game.userName);
        }else{
            userNameValid = false;
            Game.logger.warn("The entered username is not valid");
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        if(!userNameValid){
            g.setColor(Color.RED);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            int textWidth = g.getFontMetrics().stringWidth("Please enter a valid user name!");
            g.drawString("Please enter a valid user name!", Game.WIDTH / 2 - textWidth / 2, y + 90);
        }
        g.drawImage(tex.buttonImages[0], x, y, null);
    }
}
