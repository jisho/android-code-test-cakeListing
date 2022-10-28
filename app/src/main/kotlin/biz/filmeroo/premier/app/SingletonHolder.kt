package biz.filmeroo.premier.app

object SingletonHolder {
    var genreMap: HashMap<Int, String>? = null
    var instance: SingletonHolder? = null
        get() {
            if (field == null) {
                synchronized(SingletonHolder::class.java) {
                    if (field == null) {
                        field = SingletonHolder
                    }
                }
            }
            return field
        }
        private set
}