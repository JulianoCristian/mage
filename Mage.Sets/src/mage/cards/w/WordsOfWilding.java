/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.w;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.game.permanent.token.BearToken;
import mage.constants.TargetController;
import mage.abilities.dynamicvalue.common.StaticValue;

/**
 *
 * @author L_J
 */
public class WordsOfWilding extends CardImpl {

    public WordsOfWilding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");


        // {1}: The next time you would draw a card this turn, create a 2/2 green Bear creature token instead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WordsOfWildingEffect(), new ManaCostsImpl("{1}")));
    }

    public WordsOfWilding(final WordsOfWilding card) {
        super(card);
    }

    @Override
    public WordsOfWilding copy() {
        return new WordsOfWilding(this);
    }
}

class WordsOfWildingEffect extends ReplacementEffectImpl {

    public WordsOfWildingEffect() {
        super(Duration.EndOfTurn, Outcome.Discard);
        staticText = "The next time you would draw a card this turn, create a 2/2 green Bear creature token instead.";
    }

    public WordsOfWildingEffect(final WordsOfWildingEffect effect) {
        super(effect);
    }

    @Override
    public WordsOfWildingEffect copy() {
        return new WordsOfWildingEffect(this);
    }

    
    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) { 
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
			new CreateTokenEffect(new BearToken()).apply(game, source);
			this.used = true;
			discard();
			return true;
        }
        return false;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }   

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used) {
			return source.getControllerId().equals(event.getPlayerId());
        }
        return false;
    }
}