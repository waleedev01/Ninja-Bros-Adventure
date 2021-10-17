package game.other;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.objects.Ninja;
import game.other.GameView;
import org.jbox2d.common.Vec2;
/**
 * Tracker
 */
public class Tracker implements StepListener {
    private GameView view;
    private Ninja ninja;
    public Tracker(GameView view, Ninja ninja) {
        this.view = view;
        this.ninja = ninja;
    }
    public void preStep(StepEvent e) {}
    public void postStep(StepEvent e) {
        view.setCentre(new Vec2(ninja.getPosition()));
    }
}