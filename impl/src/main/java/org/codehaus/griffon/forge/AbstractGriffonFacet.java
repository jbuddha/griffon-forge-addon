package org.codehaus.griffon.forge;

import org.codehaus.griffon.types.FrameworkTypes;
import org.codehaus.griffon.types.LanguageTypes;
import org.jboss.forge.addon.dependencies.Coordinate;
import org.jboss.forge.addon.dependencies.DependencyQuery;
import org.jboss.forge.addon.dependencies.DependencyRepository;
import org.jboss.forge.addon.dependencies.DependencyResolver;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.dependencies.builder.DependencyQueryBuilder;
import org.jboss.forge.addon.dependencies.util.NonSnapshotDependencyFilter;
import org.jboss.forge.addon.facets.AbstractFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.addon.resource.DirectoryResource;

import javax.inject.Inject;
import java.util.List;

public abstract class AbstractGriffonFacet extends AbstractFacet<Project>
        implements ProjectFacet, GriffonFacet {

    private static final DependencyRepository dependencyRepository = new DependencyRepository("jcenter", "http://jcenter.bintray.com/");

    public static final String GRIFFON_JAVAFX = "org.codehaus.griffon:griffon-javafx";
    public static final String GRIFFON_GUICE = "org.codehaus.griffon:griffon-guice";
    public static final String GRIFFON_CORE_TEST = "org.codehaus.griffon:griffon-core-test";
    public static final String GROOVY_ALL = "org.codehaus.groovy:groovy-all";
    public static final String LOG4J = "log4j:log4j";
    public static final String SLF4J_LOG4J12 = "org.slf4j:slf4j-log4j12";
    public static final String SPOCK_CORE = "org.spockframework:spock-core";
    public static final String JUNIT = "junit:junit";
    public static final String GRIFFON_CORE_COMPILE = "org.codehaus.griffon:griffon-core-compile";

    private DependencyBuilder builder;
    private DependencyInstaller installer;

    FrameworkTypes framework;
    LanguageTypes language;

    @Inject
    private DependencyResolver dependencyResolver;

    @Inject
    public AbstractGriffonFacet(final DependencyInstaller installer) {
        this.installer = installer;
    }

    @Override
    public boolean install() {
        createFolders();
        addDependencies();
        return true;
    }

    @Override
    public boolean isInstalled() {
        return false;
    }

    @Override
    public String toString() {
        return getVersion().toString();
    }

    private void createFolders() {
        Project selectedProject = getFaceted();
        DirectoryResource directoryResource = (DirectoryResource) selectedProject.getRoot();
        directoryResource.getOrCreateChildDirectory("griffon-app/conf");
        directoryResource.getOrCreateChildDirectory("griffon-app/cotrollers");
        directoryResource.getOrCreateChildDirectory("griffon-app/i18n");
        directoryResource.getOrCreateChildDirectory("griffon-app/lifestyle");
        directoryResource.getOrCreateChildDirectory("griffon-app/models");
        directoryResource.getOrCreateChildDirectory("griffon-app/resources");
        directoryResource.getOrCreateChildDirectory("griffon-app/resources/org");
        directoryResource.getOrCreateChildDirectory("griffon-app/resources/org/example");
        directoryResource.getOrCreateChildDirectory("griffon-app/services");
        directoryResource.getOrCreateChildDirectory("griffon-app/views");
        directoryResource.getOrCreateChildDirectory("maven");
        directoryResource.getOrCreateChildDirectory("config");
    }

    private void addDependencies() {
        builder = DependencyBuilder.create();

        addDependency(GRIFFON_CORE_COMPILE);
        addDependency(GRIFFON_JAVAFX);
        addDependency(GRIFFON_GUICE);
        addDependency(GRIFFON_CORE_TEST);
        addDependency(GROOVY_ALL);
        addDependency(LOG4J);
        addDependency(SLF4J_LOG4J12);
        addDependency(SPOCK_CORE);
        addDependency(JUNIT);
    }

    private void addDependency(String coordinate) {
        DependencyQuery query = DependencyQueryBuilder
                .create(coordinate)
                .setFilter(new NonSnapshotDependencyFilter())
                .setRepositories(dependencyRepository);
        List<Coordinate> coordinates = dependencyResolver.resolveVersions(query);
        builder.setCoordinate(coordinates.get(coordinates.size() - 1));

        installer.install(getFaceted(), builder);
    }

    @Override
    public void setFramework(FrameworkTypes framework) {
        this.framework = framework;
    }

    @Override
    public void setLanguage(LanguageTypes language) {
        this.language = language;
    }
}