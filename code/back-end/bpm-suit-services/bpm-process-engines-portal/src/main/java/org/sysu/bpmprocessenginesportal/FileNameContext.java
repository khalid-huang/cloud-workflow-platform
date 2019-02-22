package org.sysu.bpmprocessenginesportal;

public class FileNameContext {
    //for windows
    public static String prefix = "E:\\workspace\\temp\\admission\\";
    public static String rtl = "rtl\\";
    //for linux
//    public static String prefix = "/home/stack/hqk/admission/";
//    public static String rtl = "rtl/";
    //ActivitiService
    //用于记录各个rtl级别的请求的响应时间
    public static String fileNameForRTL0 = prefix +  rtl + "rtl0.txt";
    public static String fileNameForRTL1 = prefix + rtl + "rtl1.txt";
    public static String fileNameForRTL2 = prefix + rtl + "rtl2.txt";

    //ActivitiExecuteAdmissionor
    //记录请求的原始波形
    public static String fileNameForOriginalWaveForm = prefix + "originalWaveForm.txt";
    //记录请求的平滑后的波形
    public static String fileNameForSmoothWaveForm = prefix + "smoothWaveForm.txt";
    //记录各个延迟队列的请求数
    public static String fileNameForDelayQueuesSize = prefix + "delayQueuesSize.txt";

}
