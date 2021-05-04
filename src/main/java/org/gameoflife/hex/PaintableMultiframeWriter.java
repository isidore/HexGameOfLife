package org.gameoflife.hex;

import com.spun.swing.Paintable;
import com.spun.util.ObjectUtils;
import com.spun.util.persistence.ExecutableQuery;
import org.approvaltests.approvers.ApprovalApprover;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.writers.PaintableApprovalWriter;
import org.lambda.functions.Function1;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;
import java.util.Map;

public class PaintableMultiframeWriter implements ApprovalWriter {
    private int numberOfFrames;
    private Function1<Integer, Paintable> frameGetter;

    public PaintableMultiframeWriter(int numberOfFrames, Function1<Integer, Paintable> frameGetter) {
        this.numberOfFrames = numberOfFrames;
        this.frameGetter = frameGetter;
    }

    @Override
    public File writeReceivedFile(File received) {
        try {
            ImageOutputStream output = new FileImageOutputStream(received);


            BufferedImage image = PaintableApprovalWriter.drawComponent(frameGetter.call(0));
            GifSequenceWriter writer =
                    new GifSequenceWriter(output, image.getType(), 1, true);
            writer.writeToSequence(image);
            for (int i = 1; i < numberOfFrames; i++) {
                image = PaintableApprovalWriter.drawComponent(frameGetter.call(i));
                writer.writeToSequence(image);

            }

            writer.close();
            output.close();

            return received;
        } catch (Exception e) {
            throw ObjectUtils.throwAsError(e);
        }
    }

    @Override
    public String getFileExtensionWithDot() {
        return ".gif";
    }
}
