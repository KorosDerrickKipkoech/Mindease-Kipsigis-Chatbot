package com.mindease.kipsigis

data class GeminiResponse(
    val candidates: List<Candidate>
)

data class Candidate(
    val content: CandidateContent
)

data class CandidateContent(
    val parts: List<CandidatePart>
)

data class CandidatePart(
    val text: String
)
