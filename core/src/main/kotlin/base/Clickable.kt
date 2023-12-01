package gaia.base

interface Clickable {
    var enabled: Boolean
    /**
     * @return true to consume the click
     */
    fun onClick(): Boolean
    fun onRelease()
}
