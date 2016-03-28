package edu.ustc.server.controller;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@EnableAutoConfiguration
@RequestMapping("/qrcode")
@Api("二维码相关操作")
public class QRCodeController {
	
	private static final Logger logger = LoggerFactory.getLogger(QRCodeController.class);
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    @ApiOperation(value="获取二维码图片")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public void generateQRCode(@ApiParam(value = "pid") @PathVariable("pid") Integer pid, HttpServletResponse response) {
		
		logger.info("generate QRCode...");
		
		try {
			String content = "http://localhost:9001/swagger-ui.html";
			int width = 160;
			int height = 160;
			String format = "png";
			
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			
			response.setContentType("image/png");
			OutputStream stream = response.getOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
		} catch (Exception e) {
			logger.error("generate QRCode error, {}", e.getMessage());
		}
    }
}