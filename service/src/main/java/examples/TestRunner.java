package examples;

import io.vertx.core.Starter;
import io.vertx.core.impl.Args;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by levin on 5/21/2015.
 */
public class TestRunner {

    public static void main(String[] args) throws FileNotFoundException {

        String[] runCfg = new String[]{
                "-instances",
                "2",
//                "-cluster",
                "-conf",
                getFilePath("conf", "conf.json")
        };

        String[] runCmds = new String[]{
                "run", "com.glt.cronjob.JobServiceVerticle"
        };

        Starter starter = new Starter();
        starter.run(new Args(runCfg), runCmds);

    }

    public static String getFilePath(String ...path) {
        Path currentRelativePath = Paths.get("", path);
        return currentRelativePath.toAbsolutePath().toString();
    }
}
