import griffon.util.AbstractMapResourceBundle;

import javax.annotation.Nonnull;
import java.util.Map;

import static java.util.Arrays.asList;
import static griffon.util.CollectionUtils.map;

public class Config extends AbstractMapResourceBundle {
    @Override
    protected void initialize(@Nonnull Map<String, Object> entries) {
        map(entries)
            .e("application", map()
                .e("title", "${projectname}")
                .e("startupGroups", asList("${simplifiedprojectname}"))
                .e("autoShutdown", true)
            )
            .e("mvcGroups", map()
                .e("${simplifiedprojectname}", map()
                    .e("model", "${modelclass}")
                    .e("view", "${viewclass}")
                    .e("controller", "${controllerclass}")
                )
            );
    }
}