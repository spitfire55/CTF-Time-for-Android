package re.spitfy.ctftime.data.mapper

import io.reactivex.Flowable
import re.spitfy.ctftime.data.Member
import re.spitfy.ctftime.data.Score
import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.data.db.entity.*

fun List<Team>.toTeamEntities(): List<TeamEntity> = this.map {
    TeamEntity(
            id = it.id!!.toInt(),
            academic = it.Academic,
            aliases = TypeConverters.fromStringsToJsonString(it.Aliases),
            countryCode = it.CountryCode,
            description = it.Description,
            email = it.Email,
            icq = it.ICQ,
            jabber = it.Jabber,
            linkedin = it.LinkedIn,
            logo = it.Logo,
            name = it.Name,
            nameCaseInsensitive = it.NameCaseInsensitive,
            otherLinks = TypeConverters.fromStringsToJsonString(it.OtherLinks),
            scores = TypeConverters.fromScoresToJsonString(it.Scores),
            skype = it.Skype,
            telegram = it.Telegram,
            twitter = it.Twitter,
            website = it.Website
    )
}

fun List<Team>.toScoreEntities(): List<ScoreEntity> {
    return this.flatMap { team ->
        team.Scores.map { ScoreEntity(
                year = it.key,
                points = it.value.Points,
                rank = it.value.Rank,
                teamId = team.id!!.toInt()
        ) }
    }
}

fun List<Team>.toMemberEntities(): List<MemberEntity> {
    return this.flatMap {
        it.Members.map{ member ->
            MemberEntity(
                    id = member.Id,
                    name = member.Name
            ) }
    }
}

fun List<Team>.toTeamMemberJoinEntities(): List<TeamMemberJoinEntity> {
    return this.flatMap { team ->
        team.Members.map { member ->
            TeamMemberJoinEntity(
                    teamId = team.id,
                    memberId = member.Id
            ) }
    }
}

fun Flowable<List<TeamWithMembers>>.toTeams(members: List<Member>): Flowable<List<Team>> = map {
    teamWithMembers -> teamWithMembers.map { it.toTeam(members) }
}

fun TeamWithMembers.toTeam(members: List<Member>): Team {
    return Team(
            Academic = this.team.academic,
            Aliases = TypeConverters.fromJsonStringToStrings(this.team.aliases),
            CountryCode = this.team.countryCode,
            Description = this.team.description,
            Email = this.team.email,
            ICQ = this.team.icq,
            Jabber = this.team.jabber,
            LinkedIn = this.team.linkedin,
            Logo = this.team.logo,
            Members = members.filter { this.memberIdList.contains(it.Id) },
            Name = this.team.name,
            NameCaseInsensitive = this.team.nameCaseInsensitive,
            OtherLinks = TypeConverters.fromJsonStringToStrings(this.team.otherLinks),
            Scores = TypeConverters.fromJsonStringToScores(this.team.scores),
            Skype = this.team.skype,
            Telegram = this.team.telegram,
            Twitter = this.team.twitter,
            Website = this.team.website
    ).withId(this.team.id)
}

fun MemberEntity.toMember(): Member = Member(Id = this.id, Name = this.name)