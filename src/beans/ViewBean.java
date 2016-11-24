import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;

import org.primefaces.context.RequestContext;

import fr.bluelabs.oncall.convertors.EncryptedStringConverter;
import fr.bluelabs.oncall.entities.sms.SMS;

@ManagedBean
public class ViewBean implements Serializable
{
	private static final long	serialVersionUID	= -7651591785079447561L;

	private String				mot;
	private String				crypt;

	public void viewSMSDetail(SMS sms)
	{
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("resizable", true);
		options.put("draggable", false);
		List<String> values = new ArrayList<String>();
		values.add(String.valueOf(sms.getId()));
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		params.put("smsid", values);
		RequestContext.getCurrentInstance().openDialog("viewSmsDetail", options, params);
	}

	public String getMot()
	{
		return mot;
	}

	public void setMot(String mot)
	{
		this.mot = mot;
	}

	public String getCrypt()
	{
		return crypt;
	}

	public void setCrypt(String crypt)
	{
		this.crypt = crypt;
	}

	public void Doit()
	{
		EncryptedStringConverter esc = new EncryptedStringConverter();

		this.crypt = esc.convertToDatabaseColumn(this.mot);

	}
}
