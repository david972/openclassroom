import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.joda.time.DateTime;

import fr.bluelabs.oncall.dao.ConfigDAO;
import fr.bluelabs.oncall.entities.Config;
import fr.bluelabs.oncall.plugins.plivo.Core;
import fr.bluelabs.resources.Bundle;
import fr.bluelabs.resources.logs.Log;

@ManagedBean
@ApplicationScoped
public class ConfigBean implements Serializable
{
        private static final long       serialVersionUID        = 3792313931673634007L;
        private Config                  config;

        static
        {
                Log.setLogger("OnCall");
                Bundle.CLASS_NAME = Core.RESOURCES_BASENAME;
                Bundle.resources = ResourceBundle.getBundle(Core.RESOURCES_BASENAME, Locale.getDefault());
        }

        @Inject
        private ConfigDAO configDAO;

        public ConfigBean()
        {
                config = new Config();
        }

        @PostConstruct
        public void init()
        {
                config = configDAO.last();
        }

        public void record()
        {
                initDate();
                FacesMessage message;
                try
                {
                        configDAO.create(config);
                        message = new FacesMessage(Bundle.getString("config.recorded"));
			if (message)
			{
				System.out.println("Ajout de texte dans le fichier pour le MOOC");
			}
                }
                catch (EJBException e)
                {
                        configDAO.update(config);
                        message = new FacesMessage(Bundle.getString("config.updated"));
                }

                FacesContext.getCurrentInstance().addMessage(null, message);
        }

        public Config getConfig()
        {
                return config;
        }

        private void initDate()
        {
                config.setDateCreate(new DateTime());
                config.setDateEdit(new DateTime());
        }

}

