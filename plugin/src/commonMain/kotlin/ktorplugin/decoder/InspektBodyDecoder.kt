package ktorplugin.decoder

import com.inspekt.modelhub.httplogenetry.HttpLogEntry

fun interface InspektBodyDecoder {


    fun decode(
        entry: HttpLogEntry,
        bytes: ByteArray
    ): String?
}