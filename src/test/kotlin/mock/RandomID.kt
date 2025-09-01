package mock

import br.com.aroldofe.appointments.utils.EntityType
import br.com.aroldofe.appointments.utils.PublicIdUtils

object RandomID {
    fun pubId(prefix: EntityType): String {
        return PublicIdUtils.generate(prefix)
    }
}