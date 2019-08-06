package edu.ustc.server.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class NIOStart {

    public static void main(String[] args) throws Exception {

        FileInputStream inf = new FileInputStream("/Users/anmy/workspace/workspace-2019/micro-service/micro-service-server/src/main/resources/logback.xml");
        FileChannel inFc = inf.getChannel();
        FileOutputStream outf = new FileOutputStream("/Users/anmy/workspace/workspace-2019/micro-service/micro-service-server/src/main/resources/logback-bak.xml");
        FileChannel outFc = outf.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Charset charSet = Charset.forName("utf-8");
        CharsetDecoder decoder = charSet.newDecoder();
        CharsetEncoder encoder = charSet.newEncoder();

        while (true) {

            buffer.clear();
            CharBuffer cb = decoder.decode(buffer);
            ByteBuffer bb = encoder.encode(cb);

            int t = inFc.read(bb);
            if (t == -1) {
                break;
            }

            bb.flip();
            outFc.write(bb);
        }
    }
}
