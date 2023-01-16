package pers.xds.wtuapp.web.service.impl;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ses.v20201002.SesClient;
import com.tencentcloudapi.ses.v20201002.models.SendEmailRequest;
import com.tencentcloudapi.ses.v20201002.models.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.service.EmailService;
import pers.xds.wtuapp.web.service.config.email.CommodityLockTemplateData;
import pers.xds.wtuapp.web.service.config.email.EmailBindTemplateData;
import pers.xds.wtuapp.web.service.config.props.EmailConfigurationProperties;
import pers.xds.wtuapp.web.database.util.Jackson;


/**
 * @author DeSen Xu
 * @date 2022-12-04 22:32
 */
@Service
public class TencentCouldEmailServiceImpl implements EmailService {

    private Credential credential;

    private EmailConfigurationProperties props;


    @Value("${app.meta.cdn-url}")
    public String cdnUrl;

    @Autowired
    public void setEmailConfigurationProperties(EmailConfigurationProperties config) {
        this.credential = new Credential(config.getSecretId(), config.getSecretKey());
        this.props = config;
    }

    @Override
    public void sendCommodityLockTip(String email, CommodityLockTemplateData data) {
        // 拼接cdn域名
        data.image = cdnUrl + data.image;
        // 发邮件
        SesClient sesClient = new SesClient(credential, props.getRegion());
        SendEmailRequest request = new SendEmailRequest();
        Template template = new Template();
        template.setTemplateID(props.getCommodityLockTipTemplate());
        template.setTemplateData(data.toJson());
        request.setTemplate(template);
        request.setSubject("你的商品" + data.itemName + "有新的订单");
        request.setFromEmailAddress(props.getSenderEmail());
        request.setDestination(new String[]{ email });
        try {
            sesClient.SendEmail(request);
        } catch (TencentCloudSDKException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmailCaptcha(String email, EmailBindTemplateData data) {
        SesClient sesClient = new SesClient(credential, props.getRegion());
        SendEmailRequest request = new SendEmailRequest();
        Template template = new Template();
        template.setTemplateID(props.getEmailCaptchaTemplate());
        template.setTemplateData(Jackson.toJsonString(data));

        request.setTemplate(template);
        request.setSubject("您正在绑定邮箱");
        request.setFromEmailAddress(props.getSenderEmail());
        request.setDestination(new String[]{ email });
        try {
            sesClient.SendEmail(request);
        } catch (TencentCloudSDKException e) {
            throw new RuntimeException(e);
        }
    }
}
