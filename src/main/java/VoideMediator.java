import com.lime.zeromvc.Mediator;

/**
 * Created by lime on 15/2/25.
 */
public abstract class VoideMediator extends Mediator<VoideCommandKey,VoideMediatorKey> {

    public VoideMediator (){
        super("one");
    }
}
