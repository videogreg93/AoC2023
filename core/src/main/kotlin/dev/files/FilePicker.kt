package dev.files

import com.badlogic.gdx.Gdx
import java.io.File
import javax.swing.JFileChooser
import javax.swing.JFrame

fun chooseFile(startingDirectory: String = "ProtoGaia/scenes", callback: (File) -> Unit) {
    val chooser = JFileChooser(Gdx.files.external(startingDirectory).file())
    val f = JFrame()
    f.isVisible = true
    f.toFront()
    f.isVisible = false
    val res = chooser.showOpenDialog(f)
    f.dispose()
    if (res == JFileChooser.APPROVE_OPTION) {
        // Create file if it doesnt exist
        if (!chooser.selectedFile.exists()) {
            chooser.selectedFile.createNewFile()
        }
        callback(chooser.selectedFile)
    }
}
