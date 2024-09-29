package com.marcel.fido.sources.domain

import kotlinx.serialization.Serializable

@Serializable
data class Source(val id: String, val name: String)
