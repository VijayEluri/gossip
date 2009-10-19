/*
 * Copyright (C) 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sonatype.gossip.model.io.xpp3

import org.junit.Test
import org.sonatype.gossip.model.Model

/**
 * Tests for {@link GossipXpp3Reader}.
 *
 * @author <a href="mailto:jason@planet57.com">Jason Dillon</a>
 */
class GossipXpp3ReaderTest
{
    @Test
    void testLoadXml() {
        URL url = getClass().getResource("gossip1.xml")
        GossipXpp3Reader reader = new GossipXpp3Reader()
        Model model = reader.read(url.openStream())
        println(model)
    }
}