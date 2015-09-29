package info.smartkit.eip.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import info.smartkit.eip.models.Invoice;
import info.smartkit.eip.models.InvoiceDao;
import info.smartkit.eip.settings.FolderSetting;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wordnik.swagger.annotations.ApiOperation;

@RestController
// @RequestMapping("/upload")
public class FileUploadController
{
    // Enum for image size.
    enum ImageSize
    {
        ori,
        sml,
        ico
    }

    //
    private static Logger LOG = LogManager.getLogger(FileUploadController.class);

    // Autowire an object of type InvoiceDao
    @Autowired
    private InvoiceDao _invoiceDao;

    //
    @Autowired
    ServletContext servletContext;

    //
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Response a string describing invoices pictures is successfully get or not.")
    public Invoice provideUploadInfo(@PathVariable("id") long id)
    {
        return _invoiceDao.findById(id);
    }

    //
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing invoice' picture is successfully uploaded or not.")
    public @ResponseBody Invoice handleSingleFileUpload(@RequestParam("name") String name,
        @RequestParam("owner") String owner, @RequestParam("file") MultipartFile file)
    {
        Invoice invoiceResp = null;
        if (!file.isEmpty()) {
            // ImageMagick convert options; @see:
            // http://paxcel.net/blog/java-thumbnail-generator-imagescalar-vs-imagemagic/
            Map<String, String> _imageMagickOutput = this.fileOperation(file);
            // Save to database.
            try {
                invoiceResp = new Invoice(file.getOriginalFilename(), owner, _imageMagickOutput);
                _invoiceDao.save(invoiceResp);
                LOG.info("_invoiceDao save success.");
            } catch (Exception ex) {
                LOG.error(ex.toString());
            }
            return invoiceResp;
        } else {
            LOG.error("You failed to upload " + name + " because the file was empty.");

        }
        return invoiceResp;
    }

    // @see:
    // http://www.concretepage.com/spring-4/spring-4-mvc-single-multiple-file-upload-example-with-tomcat
    @RequestMapping(value = "/uploads", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing invoice' pictures is successfully uploaded or not.")
    public @ResponseBody List<Invoice> handleMultiFileUpload(@RequestParam("name") String name,
        @RequestParam("owner") String owner, @RequestParam("files") MultipartFile[] files)
    {
        @SuppressWarnings("serial")
        ArrayList<Invoice> invoiceResps = new ArrayList<Invoice>()
        {
        };
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                if (!files[i].isEmpty()) {
                    // ImageMagick convert options; @see:
                    // http://paxcel.net/blog/java-thumbnail-generator-imagescalar-vs-imagemagic/
                    Map<String, String> _imageMagickOutput = this.fileOperation(files[i]);
                    // Save to database.
                    try {
                        Invoice invoiceResp = new Invoice(files[i].getOriginalFilename(), owner, _imageMagickOutput);
                        _invoiceDao.save(invoiceResp);
                        LOG.info("_invoiceDao save success.");
                        invoiceResps.add(invoiceResp);
                    } catch (Exception ex) {
                        LOG.error(ex.toString());
                    }
                } else {
                    LOG.error("You failed to upload " + files.toString() + " because the file was empty.");

                }
            }
        }
        return invoiceResps;
    }

    //
    private String thumbnailImage(int width, int height, String source) throws IOException, InterruptedException,
        IM4JavaException
    {
        //
        String small4dbBase =
            FilenameUtils.getBaseName(source) + "_" + String.valueOf(width) + "x" + String.valueOf(height) + "."
                + FilenameUtils.getExtension(source);
        String small4db = folderSetting.getUploads() + small4dbBase;
        String small = getClassPath() + small4db;
        // @see:
        // http://paxcel.net/blog/java-thumbnail-generator-imagescalar-vs-imagemagic/
        ConvertCmd cmd = new ConvertCmd();
        // cmd.setSearchPath("");
        File thumbnailFile = new File(small);
        if (!thumbnailFile.exists()) {
            IMOperation op = new IMOperation();
            op.addImage(source);
            op.thumbnail(width);
            op.addImage(small);
            cmd.run(op);
            LOG.info("ImageMagick success result:" + small);
        }
        return small4dbBase;
    }

    // private String getWorkingDir(){
    // String workingDir = System.getProperty("user.dir");
    // return workingDir;
    // }

    public String getClassPath()
    {
        String classPath = this.getClass().getResource("/").getPath();
        return classPath;
    }

    @Autowired
    private FolderSetting folderSetting;

    private Map<String, String> fileOperation(MultipartFile file)
    {
        Map<String, String> _imageMagickOutput = new HashMap<String, String>();
        String dbFileName = null;
        String fullFileName = null;
        try {
            byte[] bytes = file.getBytes();
            String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileNameAppendix =
                new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date()) + "." + fileExt;
            //
            dbFileName = folderSetting.getUploads() + fileNameAppendix;
            fullFileName = getClassPath() + dbFileName;
            //
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fullFileName)));
            stream.write(bytes);
            stream.close();
            LOG.info("Upload file success." + fullFileName);
            // ImageMagick convert options; @see:
            // http://paxcel.net/blog/java-thumbnail-generator-imagescalar-vs-imagemagic/
            _imageMagickOutput.put(ImageSize.ori.toString(), fileNameAppendix);
            _imageMagickOutput.put(ImageSize.sml.toString(), thumbnailImage(150, 150, fullFileName));
            _imageMagickOutput.put(ImageSize.ico.toString(), thumbnailImage(32, 32, fullFileName));
            return _imageMagickOutput;
        } catch (Exception e) {
            LOG.error("You failed to convert " + fullFileName + " => " + e.toString());
        }
        return _imageMagickOutput;
    }
}
