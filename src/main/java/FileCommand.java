/**
 * Created by linming on 15-3-1.
 */
public class FileCommand extends VoideCommand {
    @Override
    protected void init() {

    }

    public void newFile(){
        command(VoideCommandKey.ADD_TYPE,"Root");
        activate(VoideMediatorKey.HOME);
    }
    public void openFile(){

    }

}
