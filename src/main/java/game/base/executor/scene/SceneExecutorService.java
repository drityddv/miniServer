package game.base.executor.scene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.executor.command.AbstractCommand;
import game.base.executor.command.ICommand;

/**
 * @author : ddv
 * @since : 2019/6/19 上午12:46
 */
@Component
public class SceneExecutorService implements ISceneExecutorService {

    @Autowired
    private SceneExecutor sceneExecutor;

    @Override
    public void submit(ICommand command) {
        // if (command instanceof AbstractSceneRateCommand) {
        // AbstractSceneRateCommand sceneRateCommand = (AbstractSceneRateCommand)command;
        // sceneExecutor.schedule(sceneRateCommand, sceneRateCommand.getDelay(), sceneRateCommand.getPeriod());
        // } else if (command instanceof AbstractSceneDelayCommand) {
        // AbstractSceneDelayCommand sceneDelayCommand = (AbstractSceneDelayCommand)command;
        // sceneExecutor.schedule(sceneDelayCommand, sceneDelayCommand.getDelay());
        // } else {
        sceneExecutor.addTask(command);
        // }

    }

    @Override
    public void schedule(AbstractCommand command, long delay) {
        sceneExecutor.schedule(command, delay);
    }

    @Override
    public void init() {
        // 自行初始化
    }

    @Override
    public void shutdown() {
        sceneExecutor.shutdown();
    }
}
