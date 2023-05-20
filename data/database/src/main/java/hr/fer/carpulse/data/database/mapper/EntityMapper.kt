package hr.fer.carpulse.data.database.mapper

interface EntityMapper<T, V> {

    fun toEntity(data: T): V

    fun toEntities(data: Collection<T>): List<V> {
        return data.map { toEntity(it) }
    }

    fun fromEntity(entity: V): T

    fun fromEntities(entities: List<V>): List<T> {
        return entities.map { fromEntity(it) }
    }
}