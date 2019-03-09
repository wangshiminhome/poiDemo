package com.wsm.poidemo.poi.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class FileUtils {

    /**
     * 根据byte数组，生成文件
     */
    public static void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 清空文件夹内容
     * @param path  需要清空文件夹路径
     * @return
     */
    public static boolean deleteDir(String path){
        File file = new File(path);
        if(!file.exists()){//判断是否待删除目录是否存在
            System.err.println("The dir are not exists!");
            return false;
        }

        String[] content = file.list();//取得当前目录下所有文件和文件夹
        for(String name : content){
            File temp = new File(path, name);
            if(temp.isDirectory()){//判断是否是目录
                deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
                temp.delete();//删除空目录
            }else{
                if(!temp.delete()){//直接删除文件
                    System.err.println("Failed to delete " + name);
                }
            }
        }
        return true;
    }

    private static String[] getFiles(String folder) throws IOException {
        File _folder = new File(folder);
        String[] filesInFolder;

        if(_folder.isDirectory()){
            filesInFolder = _folder.list();
            return filesInFolder;
        } else {
            throw new IOException("Path is not a directory");
        }
    }

    /*
     * Java文件操作 获取文件扩展名
     *
     *  Created on: 2011-8-2
     *      Author: blueeagle
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
    /*
     * Java文件操作 获取不带扩展名的文件名
     *
     *  Created on: 2011-8-2
     *      Author: blueeagle
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

   /* public static void mergePdf(String oldFile,String newFile,String filePath,String newPdfName) throws Exception {
        //pdf合并工具类
        PDFMergerUtility mergePdf = new PDFMergerUtility();

        String[] filesInFolder = {oldFile,newFile};

        for(int i = 0; i < filesInFolder.length; i++){
            //循环添加要合并的pdf存放的路径
            mergePdf.addSource(filesInFolder[i]);
        }
        //设置合并生成pdf文件名称
        mergePdf.setDestinationFileName(filePath + File.separator + newPdfName);
        //合并pdf
        mergePdf.mergeDocuments();
    }*/

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 创建文件
     *
     * @throws IOException
     */
    public static boolean creatTxtFile(String name) throws IOException {
        boolean flag = false;
        File filename = new File(name);
        if (!filename.exists()) {
            filename.createNewFile();
            flag = true;
        }
        return flag;
    }

    /**
     * 写文件
     *
     * @param newStr
     *            新内容
     * @throws IOException
     */

    public static boolean writeTxtFile(String fileName,String newStr) throws IOException {
        // 先读取原有文件内容，然后进行写入操作
        boolean flag = false;
        String filein = newStr + "\r\n";
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            // 文件路径
            File file = new File(fileName);
            // 将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该文件原有的内容
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                // System.getProperty("line.separator")
                // 行与行之间的分隔符 相当于“\n”
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
            // TODO 自动生成 catch 块
            throw e1;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return flag;
    }

    /**
     *
     * @param pdfPath
     *            生成pdf文件
     * @param imagesPath
     *            需要转换的图片文件
     */
  /*  public static void imagesToPdf( String imagesPath,String pdfPath) {
        try {
            File file = new File(pdfPath);
            // 第一步：创建一个document对象。
            Document document = new Document();
            document.setMargins(0, 0, 0, 0);
            // 第二步：
            // 创建一个PdfWriter实例，
            PdfWriter.getInstance(document, new FileOutputStream(file));
            // 第三步：打开文档。
            document.open();
            // 第四步：在文档中增加图片。
            Image img = Image.getInstance(imagesPath);
            img.setAlignment(Image.ALIGN_CENTER);
            // 根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
//            document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
            document.setPageSize(new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight()));
            document.newPage();
            document.add(img);
            // 第五步：关闭文档。
            document.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    /**
     * 压缩图片方法
     *
     * @param oldFile
     *            将要压缩的图片
     * @param width
     *            压缩宽
     * @param height
     *            压缩长
     * @param quality
     *            压缩清晰度 <b>建议为1.0</b>
     * @param smallIcon
     *            压缩图片后,添加的扩展名
     * @return
     */
    public static String proce1(String oldFile, int width, int height, float quality,
                         String smallIcon) {
        if (oldFile == null) {
            return null;
        }
        String newImage = null;
        try {
            File file = new File(oldFile);
            if(!file.exists()) //文件不存在时
                return null;
            /** 对服务器上的临时文件进行处理 */
            BufferedImage srcFile = ImageIO.read(file);
            // 为等比缩放计算输出的图片宽度及高度
            double rate1 = ((double) srcFile.getWidth(null)) / (double) width
                    + 0.1;
            double rate2 = ((double) srcFile.getHeight(null)) / (double) height
                    + 0.1;
            double rate = rate1 > rate2 ? rate1 : rate2;
            int new_w = (int) (((double) srcFile.getWidth(null)) / rate);
            int new_h = (int) (((double) srcFile.getHeight(null)) / rate);
            /** 宽,高设定 */
            BufferedImage tag = new BufferedImage(new_w, new_h,
                    BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);
            String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
            /** 压缩后的文件名 */
            // newImage =smallIcon + filePrex
            // +oldFile.substring(filePrex.length());
            newImage = filePrex + smallIcon
                    + oldFile.substring(filePrex.length());
            // newImage = smallIcon;
            /** 压缩之后临时存放位置 */
            FileOutputStream out = new FileOutputStream(newImage);

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
            /** 压缩质量 */
            jep.setQuality(quality, true);
            encoder.encode(tag, jep);

            out.close();
            srcFile.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newImage;
    }



  /*  public void main(String[] args) throws Exception {

        //pdf合并工具类
        PDFMergerUtility mergePdf = new PDFMergerUtility();

        String folder = "F:/test";
        String destinationFileName = "mergedTest.pdf";

        String[] filesInFolder = getFiles(folder);

        for(int i = 0; i < filesInFolder.length; i++){
            //循环添加要合并的pdf存放的路径
            mergePdf.addSource(folder + File.separator + filesInFolder[i]);
        }

        //设置合并生成pdf文件名称
        mergePdf.setDestinationFileName(folder + File.separator + destinationFileName);
        //合并pdf
        mergePdf.mergeDocuments();


    }*/
}
